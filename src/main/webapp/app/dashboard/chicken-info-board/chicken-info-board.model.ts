import { BaseEntity, User } from './../../shared';

const enum State {
    'OFF',
    'ON'
}

const enum Source {
    'FANXIN'
}

export class LaborUnionBoard implements BaseEntity {
    constructor(
        public id?: number,
        public lId?: number,
        public name?: string,
        public regDate?: any,
        public state?: State,
        public type?: Source,
        public userInfos?: BaseEntity[],
        public users?: User[],
        public beanByDay?:number,
        public beanByMonth?:number,
    ) {
    }
}
