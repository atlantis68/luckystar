import { BaseEntity } from './../../shared';

const enum State {
    'OFF',
    'ON'
}

export class UserInfo implements BaseEntity {
    constructor(
        public id?: number,
        public userName?: string,
        public nickName?: string,
        public phoneNumber?: string,
        public qq?: string,
        public weiChat?: string,
        public starId?: string,
        public regDate?: any,
        public loginName?: string,
        public password?: string,
        public cookie?: string,
        public timeRate?: number,
        public beanRate?: number,
        public lastMaintain?: any,
        public state?: State,
        public taskInfos?: BaseEntity[],
        public laborUnion?: BaseEntity,
    ) {
    }
}
