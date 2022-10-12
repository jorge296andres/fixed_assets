import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { FixedAssetComponent } from './fixed-asset/fixedasset.component';
import { AreaComponent } from './area/area.component';
import { PersonComponent } from './person/person.component';
import { AppComponent } from './app.component';
import { FixedAssetsService } from './fixed-assets.service';
import { AreaService } from './area.service';
import { PersonService } from './person.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'fixed', pathMatch: 'full' },
  { path: 'area', component: AreaComponent },
  { path: 'fixed', component: FixedAssetComponent },
  { path: 'person', component: PersonComponent },
];


@NgModule({
  declarations: [
    AppComponent,
    AreaComponent,
    FixedAssetComponent,
    PersonComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    FixedAssetsService,
    AreaService,
    PersonService],
  bootstrap: [AppComponent]
})
export class AppModule { }
