import { Component, OnInit } from '@angular/core';
import { Person, ErrorResponse } from '../fixed-asset';
import { PersonService } from '../person.service';
import { HttpErrorResponse } from '@angular/common/http'
import { NgForm, FormControl } from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {
  public persons: Person[] = [];
  public editPerson!: Person | null;
  public deletePerson!: Person | null;
  public person_id: number = 0;

  public personFinded!: Person[] | null;

  public errorResponse!: ErrorResponse | null;
  public successResponse!: string | null;

  constructor(
    private personService: PersonService) { }

  ngOnInit() {
    this.getPersons();
  }

  public onOpenModal(person: Person | null, mode: string): void {
    this.errorResponse = null;
    this.successResponse = null;
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addPersonModal');
    }
    if (mode === 'edit') {
      this.editPerson = person;
      button.setAttribute('data-target', '#updatePersonModal');
    }
    if (mode === 'delete') {
      this.deletePerson = person;
      button.setAttribute('data-target', '#deletePersonModal');
    }
    container!.appendChild(button);
    button.click();
  }

  public onAddPerson(addForm: NgForm): void {
    document.getElementById('add-person-form')!.click();
    this.personService.addPerson(addForm.value).subscribe(
      (response: Person) => {
        this.getPersons();
        addForm.reset();
        this.successResponse = "Saved Succesfully";
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        addForm.reset();
      }
    );
  }

  public onUpdatePerson(person: Person): void {
    document.getElementById('edit-person-form')!.click();
    this.personService.updatePerson(person).subscribe(
      (response: Person) => {
        this.getPersons();
        this.successResponse = "Edited Succesfully";
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;

      }
    );
  }

  public getPersons(): void {
    this.personService.getPersons().subscribe(
      (response: Person[]) => {
        this.persons = response;
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;

      }
    )
  }

  public searchPerson(key: string): void {
    console.log(key);
    const results: Person[] = [];
    for (const person of this.persons) {
      if (person.person_name.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || person.document_type.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || person.document_number.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(person);
      }
    }
    this.persons = results;
    if (results.length === 0 || !key) {
      this.getPersons();
    }
  }

  public getPersonsById(person: Person): void {
    if (person.person_id == 0 || person.person_id === null) {
      this.getPersons();
    }
    this.personService.getPersonById(person.person_id).subscribe(
      (response: Person) => {
        this.persons = Array.of(response);
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        this.getPersons();
      }
    )
  }

}
