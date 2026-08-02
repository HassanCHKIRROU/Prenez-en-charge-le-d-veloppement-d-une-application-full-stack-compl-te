export interface CommentData {

    id: number;
    content: string;
    authorUsername: string;
    createdAt: string;
}




export interface CommentRequest {
    
    content: string;
}