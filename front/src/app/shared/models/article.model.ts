import { CommentData } from './comment.model';
import { Topic } from './topic.model';
import { User } from './user.model';

export interface Article {

    id: number;
    title: string;
    content: string;
    author: User;
    topic: Topic;
    createdAt: string;
    updatedAt: string;
    comments: CommentData[];
}




export interface ArticleSummary {

    id: number;
    title: string;
    content: string;
    authorUsername: string;
    topicTitle: string;
    createdAt: string;
}



export interface ArticleRequest {

    topicId: number;
    title: string;
    content: string;
}



export interface ArticleResponse extends Article {}