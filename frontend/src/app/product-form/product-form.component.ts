import { Component, OnInit } from '@angular/core';
import {ProductServiceComponent} from "../product-service/product-service.component";
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product";

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {

  product = new Product(null, "", 0);
  error = false;
  errorMessage = "";

  constructor(private productService: ProductServiceComponent,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(param =>{
      if (param['id'] != 'new') {
        this.productService.findById(param['id'])
          .subscribe(res =>{
            this.product = res;
            this.error = false;
            this.errorMessage = "";
          }, err => {
            console.log(err);
            this.error = true;
            this.errorMessage = err.statusText;
          })
      }
      })
  }

  save() {
    this.productService.save(this.product)
      .subscribe(res => {
        console.log(res)
        this.router.navigateByUrl('/product')
        this.error = false;
        this.errorMessage = "";
      }, err => {
        console.log(err);
        this.error = true;
        this.errorMessage = err.statusText;
      })
  }
}
