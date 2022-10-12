import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Area } from './fixed-asset'
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AreaService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }


  public getAreas(): Observable<Area[]> {
    return this.http.get<Area[]>(`${this.apiServerUrl}/api/v1/area/`);
  }

  public getAreaById(area_id: number): Observable<Area> {
    return this.http.get<Area>(`${this.apiServerUrl}/api/v1/area/${area_id}`);
  }

  public addArea(fixed: Area): Observable<Area> {
    return this.http.post<Area>(`${this.apiServerUrl}/api/v1/area/`, fixed);
  }

  public updateArea(area: Area): Observable<Area> {
    return this.http.put<Area>(`${this.apiServerUrl}/api/v1/area/${area.area_id}`, area);
  }

}
