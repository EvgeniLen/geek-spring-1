import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Page} from "../model/page";

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

  public delete(id: number | null){
    this.http.delete(this.baseUrl + "/" + id)
  }

  ngOnInit(): void {
  }

}
