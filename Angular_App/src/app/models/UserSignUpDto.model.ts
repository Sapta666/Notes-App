export interface UserSignUpDto {
    Username: string;
    Password: string;
    FirstName: string;
    LastName: string;
}

export function getUserSignUpInstance(): UserSignUpDto {
    return {
        Username: "",
        Password: "",
        FirstName: "",
	    LastName: "",
    }
}