import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sys-module',
        loadChildren: './sys-module/sys-module.module#ArcturusSysModuleModule'
      },
      {
        path: 'sys-access',
        loadChildren: './sys-access/sys-access.module#ArcturusSysAccessModule'
      },
      {
        path: 'sys-ip-group',
        loadChildren: './sys-ip-group/sys-ip-group.module#ArcturusSysIpGroupModule'
      },
      {
        path: 'sys-ip-group-item',
        loadChildren: './sys-ip-group-item/sys-ip-group-item.module#ArcturusSysIpGroupItemModule'
      },
      {
        path: 'sys-group',
        loadChildren: './sys-group/sys-group.module#ArcturusSysGroupModule'
      },
      {
        path: 'sys-license',
        loadChildren: './sys-license/sys-license.module#ArcturusSysLicenseModule'
      },
      {
        path: 'sys-enterprise',
        loadChildren: './sys-enterprise/sys-enterprise.module#ArcturusSysEnterpriseModule'
      },
      {
        path: 'sys-person-type',
        loadChildren: './sys-person-type/sys-person-type.module#ArcturusSysPersonTypeModule'
      },
      {
        path: 'sys-person',
        loadChildren: './sys-person/sys-person.module#ArcturusSysPersonModule'
      },
      {
        path: 'sys-user',
        loadChildren: './sys-user/sys-user.module#ArcturusSysUserModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusEntityModule {}
