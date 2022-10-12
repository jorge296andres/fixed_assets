export interface FixedAsset {
    inventory_id: number;
    description: string;
    name: string;
    serial: string;
    type: string;
    weight: number;
    height: number;
    width: number;
    length: number;
    purchase_value: number;
    purchase_date: Date;
    area: Area;
    person: Person;

}

export interface AssignFixedForm {
    area_id: number;
    person_id: number
    force_assign: boolean;
    fixed_id: number;
}

export interface Area {
    area_id: number;
    area_name: string;
    city: string;
}

export interface Person {
    person_id: number;
    person_name: string;
    document_type: string;
    document_number: string;
}

export interface ErrorResponse {
    reason: string;
    code: string;
    domain: string;
    description: string;
    timestamp: string;
}

