export interface Topic{

    id: number;
    title: string;
    description: string;
}





export interface TopicDTO extends Topic{
    
    subscribed: boolean;
}