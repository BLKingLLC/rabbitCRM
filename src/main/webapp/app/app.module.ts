import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RabbitCrmSharedModule } from 'app/shared/shared.module';
import { RabbitCrmCoreModule } from 'app/core/core.module';
import { RabbitCrmAppRoutingModule } from './app-routing.module';
import { RabbitCrmHomeModule } from './home/home.module';
import { RabbitCrmEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RabbitCrmSharedModule,
    RabbitCrmCoreModule,
    RabbitCrmHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RabbitCrmEntityModule,
    RabbitCrmAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class RabbitCrmAppModule {}
