import { Moment } from 'moment';

export interface ISysLicense {
  id?: number;
  licenseId?: number;
  status?: string;
  licenseKey?: string;
  startDate?: Moment;
  endDate?: Moment;
  mainKey?: string;
}

export class SysLicense implements ISysLicense {
  constructor(
    public id?: number,
    public licenseId?: number,
    public status?: string,
    public licenseKey?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public mainKey?: string
  ) {}
}
