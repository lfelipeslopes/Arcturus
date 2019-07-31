import { ISysGroup } from 'app/shared/model/sys-group.model';

export interface ISysAccess {
  id?: number;
  accessId?: number;
  status?: string;
  access?: string;
  description?: string;
  sysModuleId?: number;
  groupIds?: ISysGroup[];
}

export class SysAccess implements ISysAccess {
  constructor(
    public id?: number,
    public accessId?: number,
    public status?: string,
    public access?: string,
    public description?: string,
    public sysModuleId?: number,
    public groupIds?: ISysGroup[]
  ) {}
}
