export interface ISysModule {
  id?: number;
  moduleId?: number;
  moduleCode?: string;
  moduleDescription?: string;
}

export class SysModule implements ISysModule {
  constructor(public id?: number, public moduleId?: number, public moduleCode?: string, public moduleDescription?: string) {}
}
