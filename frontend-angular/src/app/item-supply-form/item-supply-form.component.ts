import { NzMessageService } from 'ng-zorro-antd';
import { ItemService } from './../_service/item.service';
import { Supply } from './../_model/supply';
import { Item } from './../_model/item';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-item-supply-form',
  templateUrl: './item-supply-form.component.html',
  styleUrls: ['./item-supply-form.component.css']
})
export class ItemSupplyFormComponent implements OnInit {

  @Input()
  loading: boolean;

  @Input()
  element: Item;

  @Output()
  load: EventEmitter<any> = new EventEmitter();

  supplyForm: FormGroup = this.builder.group({
    quantity: this.builder.control('', [Validators.required, Validators.max(999), Validators.min(1)])
  });

  constructor(private builder: FormBuilder, private service: ItemService, private message: NzMessageService) { }

  ngOnInit() {
  }

  validate() {
    this.loading = true;
    const supply: Supply = this.supplyForm.value;
    this.service.supply(this.element, supply).subscribe(() => {
      this.message.success(
        `Le matériel '${this.element.name}' a été alimenté.`
      );
      this.loading = false;
      this.load.emit(null);
    }, response => {
      this.loading = false;
      this.message.error(response.error.message);
    });
  }

  validateNumber(event) {
    const pattern = /[0-9]/;
    const inputChar = String.fromCharCode(event.charCode);
    if (event.keyCode !== 8 && !pattern.test(inputChar)) {
      event.preventDefault();
    }
  }

}
