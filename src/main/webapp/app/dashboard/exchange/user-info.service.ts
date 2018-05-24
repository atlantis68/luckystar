import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { UserInfo } from './user-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserInfoService {

    private resourceUrl = 'api/user-infos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(userInfo: UserInfo): Observable<UserInfo> {
        const copy = this.convert(userInfo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(userInfo: UserInfo): Observable<UserInfo> {
        const copy = this.convert(userInfo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<UserInfo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
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
        entity.lastMaintain = this.dateUtils
            .convertLocalDateFromServer(entity.lastMaintain);
    }

    private convert(userInfo: UserInfo): UserInfo {
        const copy: UserInfo = Object.assign({}, userInfo);
        copy.regDate = this.dateUtils
            .convertLocalDateToServer(userInfo.regDate);
        copy.lastMaintain = this.dateUtils
            .convertLocalDateToServer(userInfo.lastMaintain);
        return copy;
    }
}
