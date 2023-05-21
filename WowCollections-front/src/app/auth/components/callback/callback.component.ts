import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from '../../services/auth/auth.service';
import { Authorization } from '../../models/authorization';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, 
    private authService: AuthService,
    private router: Router) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['error']) {
        console.log(`Error: ${params['error']}`)
        return;
      }
      const code = params['code'];
      if (code) {
        this.authService.authenticate(code).subscribe(auth => {
          this.router.navigate(['/'])
        });
      }
    })
  }

}
