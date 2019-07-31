export interface ISysPerson {
  id?: number;
  personId?: number;
  personDescription?: string;
  personContact?: string;
  sysPersonTypeId?: number;
}

export class SysPerson implements ISysPerson {
  constructor(
    public id?: number,
    public personId?: number,
    public personDescription?: string,
    public personContact?: string,
    public sysPersonTypeId?: number
  ) {}
}
