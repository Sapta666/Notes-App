export interface LoginDto {
    Username: string;
	Password: string;
}

export function getLoginInstance(): LoginDto {
    return {
        Username: "",
	    Password: "",
    }
}
