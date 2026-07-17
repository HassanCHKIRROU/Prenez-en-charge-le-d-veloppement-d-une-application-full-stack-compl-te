import { Topic } from '../../shared/models/topic.model';

export interface User {
    id: number;
    username: string;
    email: string;
    createdAt: string;
    updatedAt: string;
}


export interface UserProfileResponse extends User {
    subscriptions: Topic[];
}


export interface UpdateProfileRequest {
    username: string;
    email: string;
    password: string; 

}