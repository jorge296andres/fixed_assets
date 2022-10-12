import { Component, OnInit } from '@angular/core';
import { Area, FixedAsset, Person, AssignFixedForm, ErrorResponse } from '../fixed-asset';
import { FixedAssetsService } from '../fixed-assets.service';
import { AreaService } from '../area.service';
import { PersonService } from '../person.service';
import { HttpErrorResponse } from '@angular/common/http'
import { NgForm, FormControl } from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './fixedasset.component.html',
  styleUrls: ['./fixedasset.component.css']
})
export class FixedAssetComponent implements OnInit {
  public fixedAssets: FixedAsset[] = [];
  public areas: Area[] = [];
  public persons: Person[] = [];
  public editFixed!: FixedAsset | null;
  public deleteFixed!: FixedAsset | null;
  public assignFixed!: FixedAsset | null;
  public assignFixedForm!: AssignFixedForm | null;

  public errorResponse!: ErrorResponse | null;
  public successResponse!: string | null;

  assign_to_person = new FormControl('');
  assign_to_area = new FormControl('');

  constructor(
    private fixedService: FixedAssetsService,
    private areaService: AreaService,
    private personService: PersonService) { }

  ngOnInit() {
    this.getFixedAsset();
  }

  public onOpenModal(fixed: FixedAsset | null, mode: string): void {
    this.errorResponse = null;
    this.successResponse = null;
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addFixedModal');
    }
    if (mode === 'edit') {
      this.editFixed = fixed;
      button.setAttribute('data-target', '#updateFixedModal');
    }
    if (mode === 'assignFixed') {
      this.assignFixed = fixed;
      this.getAreas();
      this.getPersons();
      button.setAttribute('data-target', '#assignFixedModal');
    }
    container!.appendChild(button);
    button.click();
  }

  public onAddFixed(addForm: NgForm): void {
    document.getElementById('add-fixed-form')!.click();
    this.fixedService.addFixeds(addForm.value).subscribe(
      (response: FixedAsset) => {
        this.getFixedAsset();
        addForm.reset();
        this.successResponse = "Saved Succesfully";
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        addForm.reset();
      }
    );
  }

  public onUpdateFixed(fixed: FixedAsset): void {
    document.getElementById('edit-fixed-form')!.click();
    this.fixedService.updateFixeds(fixed).subscribe(
      (response: FixedAsset) => {
        this.getFixedAsset();
        this.successResponse = "Edited Succesfully";
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;

      }
    );
  }

  public getFixedAsset(): void {
    this.fixedService.getFixeds().subscribe(
      (response: FixedAsset[]) => {
        this.fixedAssets = response;
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;

      }
    )
  }

  public onAssignFixed(fixed: AssignFixedForm): void {
    document.getElementById('assign-fixed-form')!.click();

    if (!this.assign_to_person?.value && this.assign_to_area?.value) {
      this.fixedService.assignFixedToArea(fixed).subscribe(
        (response: FixedAsset) => {
          this.getFixedAsset();
          this.cleanChecks();
          this.successResponse = "Assigned Succesfully";
        },
        (error: HttpErrorResponse) => {
          this.cleanChecks();
          this.errorResponse = error.error;

        }
      );
    }
    if (this.assign_to_person?.value && !this.assign_to_area?.value) {
      this.fixedService.assignFixedToPerson(fixed).subscribe(
        (response: FixedAsset) => {
          this.getFixedAsset();
          this.cleanChecks();
          this.successResponse = "Assigned Succesfully";
        },
        (error: HttpErrorResponse) => {
          this.cleanChecks();
          this.errorResponse = error.error;

        }
      );
    }

    if (!this.assign_to_person?.value && !this.assign_to_area?.value) {

      if (fixed.area_id) {
        this.fixedService.assignFixedToArea(fixed).subscribe(
          (response: FixedAsset) => {
            this.getFixedAsset();
            this.cleanChecks();
            this.successResponse = "Assigned Succesfully";
          },
          (error: HttpErrorResponse) => {
            this.cleanChecks();
            this.errorResponse = error.error;

          }
        );
      }

      if (fixed.person_id) {
        this.fixedService.assignFixedToPerson(fixed).subscribe(
          (response: FixedAsset) => {
            this.getFixedAsset();
            this.cleanChecks();
            this.successResponse = "Assigned Succesfully";
          },
          (error: HttpErrorResponse) => {
            this.cleanChecks();
            this.errorResponse = error.error;

          }
        );
      }
    }
  }

  public getAreas(): void {
    this.areaService.getAreas().subscribe(
      (response: Area[]) => {
        this.areas = response;
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;

      }
    )
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

  public searchFixed(key: string): void {
    console.log(key);
    const results: FixedAsset[] = [];
    for (const fixed of this.fixedAssets) {
      if (fixed.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || fixed.serial.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || fixed.description.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || fixed.type.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(fixed);
      }
    }
    this.fixedAssets = results;
    if (results.length === 0 || !key) {
      this.getFixedAsset();
    }
  }

  public cleanChecks() {
    this.assign_to_area.reset();
    this.assign_to_person.reset();
  }

  public getFixedById(fixed: FixedAsset): void {
    if (fixed.inventory_id == 0 || !fixed.inventory_id) {
      this.getFixedAsset();
    }
    this.fixedService.getFixedById(fixed.inventory_id).subscribe(
      (response: FixedAsset) => {
        this.fixedAssets = Array.of(response);
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        this.getFixedAsset();
      }
    )
  }

  public getFixedBySerial(fixed: FixedAsset): void {
    if (!fixed.serial) {
      this.getFixedAsset();
    }
    this.fixedService.getFixedsBySerial(fixed.serial).subscribe(
      (response: FixedAsset) => {
        this.fixedAssets = Array.of(response);
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        this.getFixedAsset();
      }
    )
  }

  public getFixedByType(fixed: FixedAsset): void {
    if (!fixed.type) {
      this.getFixedAsset();
    }
    this.fixedService.getFixedsByType(fixed.type).subscribe(
      (response: FixedAsset[]) => {
        this.fixedAssets = response;
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        this.getFixedAsset();
      }
    )
  }

  public getFixedByPurchaseDate(fixed: FixedAsset): void {
    if (!fixed.type) {
      this.getFixedAsset();
    }
    this.fixedService.getFixedsByPurchaseDate(fixed.purchase_date).subscribe(
      (response: FixedAsset[]) => {
        this.fixedAssets = response;
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        this.getFixedAsset();
      }
    )
  }

}
