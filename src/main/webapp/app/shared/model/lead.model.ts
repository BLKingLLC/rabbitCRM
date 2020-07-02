export interface ILead {
  id?: number;
  source?: string;
  lastName?: string;
  firstName?: string;
  title?: string;
  companyName?: string;
  state?: string;
  country?: string;
  county?: string;
  city?: string;
  zip?: string;
  contacted?: boolean;
  doNotContact?: boolean;
  email?: string;
  phone?: string;
}

export class Lead implements ILead {
  constructor(
    public id?: number,
    public source?: string,
    public lastName?: string,
    public firstName?: string,
    public title?: string,
    public companyName?: string,
    public state?: string,
    public country?: string,
    public county?: string,
    public city?: string,
    public zip?: string,
    public contacted?: boolean,
    public doNotContact?: boolean,
    public email?: string,
    public phone?: string
  ) {
    this.contacted = this.contacted || false;
    this.doNotContact = this.doNotContact || false;
  }
}
