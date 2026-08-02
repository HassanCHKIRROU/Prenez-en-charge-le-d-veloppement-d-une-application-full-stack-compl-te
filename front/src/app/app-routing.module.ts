import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { FeedComponent } from './pages/feed/feed.component';
import { AuthGuard } from './core/guards/auth.guard';
import { ProfileComponent } from './pages/profile/profile.component';
import { ProfileEditComponent } from './pages/profile/edit/profile-edit.component'; 
import { TopicsComponent } from './pages/topics/topics.component';
import { ArticleDetailComponent } from './pages/article/detail/article-detail.component';
import { ArticleCreateComponent } from './pages/article/create/article-create.component';



const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'feed', component: FeedComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  { path: 'topics', component: TopicsComponent, canActivate: [AuthGuard] },
  { path: 'article/create', component: ArticleCreateComponent, canActivate: [AuthGuard]},
  { path: 'article/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] },
  { path: 'profile/edit', component: ProfileEditComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' },
  
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }