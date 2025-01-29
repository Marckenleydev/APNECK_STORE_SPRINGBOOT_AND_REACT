import { IUser } from "./IUser";

export interface IUserRequest {
    email: string;
    password?:string;
    confirmPassword?: string;
   
}

export interface IRegisterRequest extends IUserRequest {
    firstName:string;
    lastName:string;
    phone?:string;
    address: Address;
}
interface Address{
     street:string;
 houseNumber:string;
 zipCode:number

}


export type EmailAddress = Pick<IUserRequest, "email">;
export type UpdatePassword =Pick<IUserRequest, "password"> &{newPassword:string, confirmNewPassword:string};
export type UpdateNewPassword = Pick<IUser, "userId"> & UpdatePassword;