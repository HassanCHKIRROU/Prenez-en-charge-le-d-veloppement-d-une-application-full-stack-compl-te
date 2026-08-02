import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../../core/services/user.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})

export class ProfileEditComponent implements OnInit {


  profileForm: FormGroup;
  loading: boolean = true;
  submitting: boolean = false;
  error: string = '';
  success: string = '';





  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private authService: AuthService,  
    private router: Router
  ) {
    this.profileForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.minLength(8),
        Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\S+$).{8,}$/)
      ]],
      confirmPassword: ['']
    }, {
      validators: this.passwordMatchValidator
    });
  }






  ngOnInit(): void {
    this.loadProfile();
  }





  passwordMatchValidator(group: FormGroup): { [key: string]: boolean } | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }





  loadProfile(): void {
    this.loading = true;
    this.userService.getProfile().subscribe({
      next: (data) => {
        this.profileForm.patchValue({
          username: data.username,
          email: data.email
        });
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement du profil';
        this.loading = false;
      }
    });
  }





  onSubmit(): void {
    if (this.profileForm.invalid) {
      return;
    }

    this.submitting = true;
    this.error = '';
    this.success = '';

    const formValue = this.profileForm.value;
    const request: any = {
      username: formValue.username.trim(),
      email: formValue.email.trim()
    };

    if (formValue.password && formValue.password.length >= 8) {
      request.password = formValue.password;
    }

    this.userService.updateProfile(request).subscribe({
      next: (data) => {
        this.success = ' Profil mis à jour avec succès !';
        this.submitting = false;
        
        //  Reconnecter l'utilisateur avec ses nouvelles identifiants
        setTimeout(() => {
          //  Déconnecter
          this.authService.logout();
          
          //  Reconnecter automatiquement
          this.authService.login({
            usernameOrEmail: data.username,
            password: formValue.password || 'ancien_mot_de_passe_non_disponible'
          }).subscribe({
            next: (loginResponse) => {
              this.authService.saveToken(loginResponse.token);
              this.router.navigate(['/profile']).then(() => {
                window.location.reload();
              });
            },
            error: () => {
              // Si la reconnexion échoue (mot de passe non fourni), rediriger vers login
              this.router.navigate(['/login']);
            }
          });
        }, 1500);
      },
      error: (err) => {
        this.error = err.error?.message || 'Erreur lors de la mise à jour du profil';
        this.submitting = false;
      }
    });
  }





  
  cancel(): void {
    this.router.navigate(['/profile']);
  }
}