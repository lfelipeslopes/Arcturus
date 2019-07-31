export interface ISysPersonType {
  id?: number;
  personTypeId?: number;
  personTypeCode?: string;
  personTypeDescription?: string;
}

export class SysPersonType implements ISysPersonType {
  constructor(public id?: number, public personTypeId?: number, public personTypeCode?: string, public personTypeDescription?: string) {}
}
