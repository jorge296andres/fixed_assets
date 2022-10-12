import { Component, OnInit } from '@angular/core';
import { Area, ErrorResponse } from '../fixed-asset';
import { AreaService } from '../area.service';
import { PersonService } from '../person.service';
import { HttpErrorResponse } from '@angular/common/http'
import { NgForm, FormControl } from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './area.component.html',
  styleUrls: ['./area.component.css']
})
export class AreaComponent implements OnInit {
  public areas: Area[] = [];
  public editArea!: Area | null;
  public deleteArea!: Area | null;

  public errorResponse!: ErrorResponse | null;
  public successResponse!: string | null;

  constructor(
    private areaService: AreaService) { }

  ngOnInit() {
    this.getAreas();
  }

  public onOpenModal(area: Area | null, mode: string): void {
    this.errorResponse = null;
    this.successResponse = null;
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addAreaModal');
    }
    if (mode === 'edit') {
      this.editArea = area;
      button.setAttribute('data-target', '#updateAreaModal');
    }
    container!.appendChild(button);
    button.click();
  }

  public onAddArea(addForm: NgForm): void {
    document.getElementById('add-area-form')!.click();
    this.areaService.addArea(addForm.value).subscribe(
      (response: Area) => {
        this.getAreas();
        addForm.reset();
        this.successResponse = "Saved Succesfully";
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        addForm.reset();
      }
    );
  }

  public onUpdateArea(area: Area): void {
    document.getElementById('edit-area-form')!.click();
    this.areaService.updateArea(area).subscribe(
      (response: Area) => {
        this.getAreas();
        this.successResponse = "Edited Succesfully";
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;

      }
    );
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

  public searchArea(key: string): void {
    console.log(key);
    const results: Area[] = [];
    for (const area of this.areas) {
      if (area.area_name.toLowerCase().indexOf(key.toLowerCase()) !== -1

        || area.city.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(area);
      }
    }
    this.areas = results;
    if (results.length === 0 || !key) {
      this.getAreas();
    }
  }

  public getAreaById(area: Area): void {
    if (area.area_id == 0 || area.area_id === null) {
      this.getAreas();
    }
    this.areaService.getAreaById(area.area_id).subscribe(
      (response: Area) => {
        this.areas = Array.of(response);
      },
      (error: HttpErrorResponse) => {
        this.errorResponse = error.error;
        this.getAreas();
      }
    )
  }

}
