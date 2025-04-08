import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-temp-cmp',
  templateUrl: './test-cmp.component.html',  
})
export class TestCmpComponent {

  @Input() pState: string = "success";

  protected name: string = "TestCmp";
   
}
