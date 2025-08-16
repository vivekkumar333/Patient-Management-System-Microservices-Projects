import { UserRole } from "../../enums/user-role.enum.js";

export interface UserRegistrationRequest{
    email: string;
    password: string;
    name: string;
    address: string;
    dateOfBirth: string;
    role: UserRole;
}