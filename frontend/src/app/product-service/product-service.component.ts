import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Page} from "../model/page";
import {Product} from "../model/product";

@Component({
  selector: 'app-product-service',
  templateUrl: './product-service.component.html',
  styleUrls: ['./product-service.component.scss']
})
export class ProductServiceComponent implements OnInit {
  private baseUrl = "api/v1/product";
  constructor(private http: HttpClient) { }

  public findAll() {
    return this.http.get<Page>(this.baseUrl);
  }

  public findById(id: number){
    return this.http.get<Product>(this.baseUrl.concat(`/${id}`))
  }

  public delete(id: number | null){
    this.http.delete(this.baseUrl.concat(`/${id}`))
  }

  public save(product: Product) {
    return this.http.put<Product>(this.baseUrl, product)
  }

  ngOnInit(): void {
  }


}
