export interface AppSettingsDto {
    Cnct_PKey: string;
	Token: string;
	Username: string;
	FirstName: string;
	LastName: string;
}

export function getAppSettingsInstance(): AppSettingsDto {
    return {        
        Cnct_PKey: "",
        Token: "",
        Username: "",
        FirstName: "",
        LastName: "",
    }
}