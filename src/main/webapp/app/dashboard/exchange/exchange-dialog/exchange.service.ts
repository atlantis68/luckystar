import {Injectable} from '@angular/core';
import {Http, Response } from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ExchangeService {

    constructor(private http: Http) {
    }

    getRemainingBean(id: number): Observable<any> {
        return this.http.get(`api/get-remaining-bean?id=${id}`).map((res: Response) => {
            return res.json();
        });
    }

    exchangeRemainingBean(id: number,bean: number): Observable<any> {
        return this.http.get(`api/exchange-remaining-bean?id=${id}&bean=${bean}`).map((res: Response) => {
            return res.json();
        });
    }

}
