import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-figure',
  templateUrl: './figure.component.html',
  styleUrls: ['./figure.component.css']
})
export class FigureComponent {

  @Input() imgSrc!: string;
  @Input() size!: string;
  @Input() borderWidth!: string;
  @Input() borderRadius!: string;

}
