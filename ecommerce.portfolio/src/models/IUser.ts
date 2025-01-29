export interface IUser {
    id: string
    userId: string
    referenceId:string
    firstName: string
    lastName: string
    email: string
    phone: string
    lastLogin: string
    createdAt: string
    updatedAt: string
    role: string
    authorities: string
    createdBy: number
    updatedBy: number
    accountNonExpired: boolean
    accountNonLocked: boolean
    credentialsNonExpired: boolean
    enabled: boolean
  }

  export type Role = {role: string};
  export type User ={user: IUser};
  export type Users = {users: IUser[]};