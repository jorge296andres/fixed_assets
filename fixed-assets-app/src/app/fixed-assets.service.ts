import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AssignFixedForm, FixedAsset } from './fixed-asset'
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FixedAssetsService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getFixeds(): Observable<FixedAsset[]> {
    return this.http.get<FixedAsset[]>(`${this.apiServerUrl}/api/v1/fixed-assets/`);
  }

  public getFixedById(inventory_id: number): Observable<FixedAsset> {
    return this.http.get<FixedAsset>(`${this.apiServerUrl}/api/v1/fixed-assets/${inventory_id}`);
  }

  public getFixedsBySerial(serial: string): Observable<FixedAsset> {
    return this.http.get<FixedAsset>(`${this.apiServerUrl}/api/v1/fixed-assets/get-by-serial/${serial}`);
  }

  public getFixedsByType(type: string): Observable<FixedAsset[]> {
    return this.http.get<FixedAsset[]>(`${this.apiServerUrl}/api/v1/fixed-assets/get-by-type/${type}`);
  }

  public getFixedsByPurchaseDate(date: Date): Observable<FixedAsset[]> {
    return this.http.get<FixedAsset[]>(`${this.apiServerUrl}/api/v1/fixed-assets/get-by-purchase-date/${date}`);
  }

  public addFixeds(fixed: FixedAsset): Observable<FixedAsset> {
    return this.http.post<FixedAsset>(`${this.apiServerUrl}/api/v1/fixed-assets/`, fixed);
  }

  public updateFixeds(fixed: FixedAsset): Observable<FixedAsset> {
    return this.http.put<FixedAsset>(`${this.apiServerUrl}/api/v1/fixed-assets/${fixed.inventory_id}`, fixed);
  }

  public assignFixedToArea(fixed: AssignFixedForm): Observable<FixedAsset> {
    return this.http.post<FixedAsset>(`${this.apiServerUrl}/api/v1/fixed-assets/assign-fixed-to-area/`, fixed);
  }

  public assignFixedToPerson(fixed: AssignFixedForm): Observable<FixedAsset> {
    return this.http.post<FixedAsset>(`${this.apiServerUrl}/api/v1/fixed-assets/assign-fixed-to-person/`, fixed);
  }
}
