import { Injectable } from '@angular/core';
import { Http, Response,ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

// import { ChickenInfo } from '../../entities/chicken-info/chicken-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AllByTokenService {

    private resourceUrl = 'api/all-by-token';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }


    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
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
    recentTime():Observable<ResponseWrapper>{
        return this.http.get("api/recent-time");
    }

}
