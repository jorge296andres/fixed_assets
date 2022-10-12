import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Person } from './fixed-asset'
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getPersons(): Observable<Person[]> {
    return this.http.get<Person[]>(`${this.apiServerUrl}/api/v1/person/`);
  }

  public getPersonById(person_id: number): Observable<Person> {
    return this.http.get<Person>(`${this.apiServerUrl}/api/v1/person/${person_id}`);
  }

  public addPerson(fixed: Person): Observable<Person> {
    return this.http.post<Person>(`${this.apiServerUrl}/api/v1/person/`, fixed);
  }

  public updatePerson(person: Person): Observable<Person> {
    return this.http.put<Person>(`${this.apiServerUrl}/api/v1/person/${person.person_id}`, person);
  }

}
