package com.vkt.pms.service;

import com.vkt.pms.request.*;
import com.vkt.pms.entity.Patient;
import com.vkt.pms.exception.BusinessException;
import com.vkt.pms.exception.EmailAlreadyExistException;
import com.vkt.pms.exception.PatientNotFoundException;
import com.vkt.pms.kafka.KafkaProducer;
import com.vkt.pms.mapper.PatientObjectMapper;
import com.vkt.pms.repository.PatientRepository;
import com.vkt.pms.response.BillingResponseDto;
import com.vkt.pms.response.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Qualifier("BillingServiceWebClient")
    private final WebClient.Builder billingServiceWebClient;

    private final KafkaProducer kafkaProducer;

    @Value("${billing.service.uri}")
    String createBillingAccountUri;

//    Random random = new Random();

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public PatientResponseDto savePatientDetails(PatientCreateRequest patientCreateRequest){
        try{
            if(patientRepository.existsByEmail(patientCreateRequest.getEmail())){
                throw new EmailAlreadyExistException("Patient with Email- "+ patientCreateRequest.getEmail()+" already registered");
            }
            Patient patient = PatientObjectMapper.patientCreateRequestDtoToPatient(patientCreateRequest);
            patient.setRegisteredDate(LocalDateTime.now());
            Patient saved =  patientRepository.save(patient);

            BillingRequest billingRequest = new BillingRequest(saved.getUid(),
                    List.of(new BillingTransactionRequest("REGISTRATION",
                            new BigDecimal("500.00"),
                            "Registration fee, First visit")));

            BillingResponseDto billingResponse = billingServiceWebClient.build()    //Webclient call to Billing-Service
                    .post()
                    .uri(createBillingAccountUri)
                    .bodyValue(billingRequest)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError,this::handelErrorResponse)
                    .bodyToMono(BillingResponseDto.class)
                    .block();
            kafkaProducer.sendPatientCreatedEvent(saved);   // Send patient created notification event
            return PatientObjectMapper.patientToPatientResponseDto(saved);
        }catch (BusinessException ex){
            throw ex;
        }catch (Exception ex){
            throw new RuntimeException("Failed to register a patient", ex);
        }
    }

    private Mono<? extends Throwable> handelErrorResponse(ClientResponse clientResponse){
        return clientResponse.bodyToMono(String.class)
                .defaultIfEmpty("Unknown Error")
                .flatMap(errorBody -> {
                    HttpStatusCode status = clientResponse.statusCode();
                    String msg = "Billing service error ["+ status.value() +"]: "+errorBody;
                    return Mono.error(new BusinessException(msg));
                });
    }

    public PatientResponseDto updatePatientDetails(Long patientId, PatientUpdateRequest patientUpdateRequest){
        Patient existingPatient =  patientRepository.findByUid(patientId)
                .orElseThrow(()-> new PatientNotFoundException("Patient not found with ID: "+patientId));
        existingPatient.setEmail(patientUpdateRequest.getEmail());
        existingPatient.setAddress(patientUpdateRequest.getAddress());
        existingPatient.setDateOfBirth(LocalDate.parse(patientUpdateRequest.getDateOfBirth()));
        existingPatient.setUpdatedTimestamp(LocalDateTime.now());
        Patient updated = patientRepository.save(existingPatient);
        return PatientObjectMapper.patientToPatientResponseDto(updated);
    }

    public List<PatientResponseDto> fetchAllPatientDetails(){
        return patientRepository.findAll()
                .stream()
                .map(PatientObjectMapper::patientToPatientResponseDto)
                .collect(Collectors.toList());
    }

    public PatientResponseDto fetchPatientDetails(Long patientId){
        return patientRepository.findByUid(patientId)
                .map(PatientObjectMapper::patientToPatientResponseDto)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: "+patientId));
    }

    public void deletePatientDetails(Long patientId){
        Patient deletePatient = patientRepository.findByUid(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: "+patientId));
        patientRepository.delete(deletePatient);
    }


//    public Long generateProductId(){
//        Long productId = 1_000_000_000L + random.nextInt(999_999_999);
//        return patientRepository.existsByPatientId(productId)? generateProductId(): productId;
//
//    }

}
