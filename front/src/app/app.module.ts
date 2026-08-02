import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { FeedComponent } from './pages/feed/feed.component';
import { JwtInterceptor } from './core/interceptors/jwt.interceptor';
import { DatePipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { ProfileEditComponent } from './pages/profile/edit/profile-edit.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { MatSnackBarModule} from '@angular/material/snack-bar'
import { ArticleCreateComponent } from './pages/article/create/article-create.component';
import { ArticleDetailComponent } from './pages/article/detail/article-detail.component';
import { MatOptionModule } from '@angular/material/core';
import { HeaderComponent } from './shared/components/header/header.component';
import { MatToolbarModule } from '@angular/material/toolbar';


@NgModule({

  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    FeedComponent,
    ProfileComponent,
    ProfileEditComponent,
    TopicsComponent,
    ArticleCreateComponent,
    ArticleDetailComponent,
    HeaderComponent
  ],


  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatIconModule,
    MatOptionModule,
    MatSnackBarModule,
    MatToolbarModule
  ],


  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }, DatePipe
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }