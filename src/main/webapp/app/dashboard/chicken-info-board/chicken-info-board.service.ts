import { Injectable } from '@angular/core';
import { Http, Response,ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

// import { ChickenInfo } from '../../entities/chicken-info/chicken-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ChickenInfoBoardService {

    private resourceUrl = 'api/chicken-infos-board';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }


    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    export(req?: any): Observable<any> {
        const options = createRequestOption(req);
        options.responseType= ResponseContentType.Blob;
       return this.http.get('api/chicken-infos-excel', options)
            .map(res => {
                return {
                    filename: '业绩排行.xls',
                    data: res.blob()
                };
            });
    }

    recentTime():Observable<ResponseWrapper>{
       return this.http.get("api/recent-time");
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.regDate = this.dateUtils
            .convertLocalDateFromServer(entity.regDate);
    }

}
