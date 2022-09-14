import { Component, OnInit } from '@angular/core';
import {Product} from "../model/product";
import {ProductServiceComponent} from "../product-service/product-service.component";
import {reduce} from "rxjs";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductServiceComponent) { }

  ngOnInit(): void {
    this.productService.findAll()
      .subscribe(response => {
        console.log(response.content)
        this.products = response.content;
      }, error => {
        console.log(error);
      })
  }

  delete(id: number | null) {
    this.productService.delete(id);
    this.ngOnInit()
  }
}
