import React, { Component } from "react";
import classes from "./CreateUpdateClinic.module.css";
import authService from "../../../services/auth-service";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHospitalSymbol, faClinicMedical, faFileAlt, faPlus, faTrashAlt, faTimes} from "@fortawesome/free-solid-svg-icons";
import {Link, withRouter} from "react-router-dom";
import axios from "axios";

class CreateUpdateClinic extends Component {

    state = {
        id: null,
        name: "",
        address: "",
        description: "",
        priceList: [],
        index: 0,
        creating: false,
        showAddNewPriceItemPopup: false,
        selectedPriceItem: {
            id: null,
            name: "",
            price: 0.0,
            description: "",
            index: 0
        },
        errorValidation: {
            clinicName: false,
            clinicAddress: false,
            clinicDescription: false,
            itemName: false,
            itemPrice: false,
            itemDescription: false
        },
        selectedUpdate: false
    }
    
    componentDidMount() {
        const creating = this.props.creating;
        this.setState({creating: creating});
        
        if(!creating) {

            let id = this.props.match.params.id;

            if(id === undefined){
                axios.get("clinics/adminsClinic")
                .then((response) =>{
                    this.setState({
                        id: response.data.id,
                        name: response.data.name,
                        address: response.data.address,
                        description: response.data.description,
                        priceList: response.data.priceList.map((item, index) => ({...item, index: index})),
                        index: response.data.priceList.length
                    });
                })
                .catch(error => console.error(error));
            }else{
                axios
                .get("/clinics/" + this.props.match.params.id)
                .then((response) => {
                    this.setState({
                        id: response.data.id,
                        name: response.data.name,
                        address: response.data.address,
                        description: response.data.description,
                        priceList: response.data.priceList.map((item, index) => ({...item, index: index})),
                        index: response.data.priceList.length
                    });
                })
                .catch(error => console.error(error));
            }

            
        }
    }
    
    clinicNameInputHandler = event => {
        let error = false;
        if(event.target.value.trim() === "")
            error = true;
        
        this.setState({ name: event.target.value, errorValidation: { ...this.state.errorValidation, clinicName:error }});
    }
    
    clinicAddressInputHandler = event => {
        let error = false;
        if(event.target.value.trim() === "")
            error = true;
        
        this.setState({ address: event.target.value, errorValidation: { ...this.state.errorValidation, clinicAddress:error }});
    }
    
    clinicDescriptionInputHandler = event => {
        let error = false;
        if(event.target.value.trim() === "")
            error = true;
        
        this.setState({ description: event.target.value, errorValidation: { ...this.state.errorValidation, clinicDescription:error }});
    }
    
    itemNameInputHandler = event => {
        let error = false;
        if(event.target.value.trim() === "")
            error = true;
        
        this.setState({ selectedPriceItem: { ...this.state.selectedPriceItem, name: event.target.value }, errorValidation: { ...this.state.errorValidation, itemName:error }});
    }
    
    itemPriceInputHandler = event => {
        let error = false;
        const number = parseFloat(event.target.value);
        if(isNaN(number) || number < 0 || event.target.value === "")
            error = true;
        
        this.setState({ selectedPriceItem: { ...this.state.selectedPriceItem, price: event.target.value }, errorValidation: { ...this.state.errorValidation, itemPrice:error }});
    }
    
    itemDescriptionInputHandler = event => {
        let error = false;
        if(event.target.value.trim() === "")
            error = true;
        
        this.setState({ selectedPriceItem: { ...this.state.selectedPriceItem, description: event.target.value }, errorValidation: { ...this.state.errorValidation, itemDescription:error }});
    }
    
    saveClinicHandler = () => {
    
        if(this.state.name === "" || this.state.address === "" || this.state.description === "") {
            alert("Validation error");
            return;
        }
        
        const clinic = {
            id: this.state.id,
            name: this.state.name,
            address: this.state.address,
            description: this.state.description,
            priceList: this.state.priceList
        }
        
        if(this.state.creating) {
            axios
                .post("/clinics", clinic)
                .then((response) => {
                    this.props.history.push("/clinics");
                })
                .catch(error => console.error(error));
        } else {
            axios
                .put("/clinics", clinic)
                .then((response) => {
                    this.props.history.push("/clinics");
                })
                .catch(error => console.error(error));
        }
    }
    
    savePriceListItem = () => {
    
        // TODO CHANGE VALIDATION SO THAT INPUTS GET RED BORDER IF SOMETHING IS WRONG
        const number = parseFloat(this.state.selectedPriceItem.price);
        if(this.state.selectedPriceItem.name === "" || this.state.selectedPriceItem.price === "" || isNaN(number) || number < 0 || this.state.selectedPriceItem.description === "") {
            alert("Validation error");
            return;
        }
        
        let newPriceList;
        if(this.state.selectedUpdate) {
            newPriceList = this.state.priceList.map(item => item.index === this.state.selectedPriceItem.index ? {...this.state.selectedPriceItem} : item);
        } else {
            newPriceList = [...this.state.priceList];
            newPriceList.push({...this.state.selectedPriceItem});
        }
        
        let newIndex = this.state.index;
        newIndex++;
        
        this.setState({
            priceList: newPriceList,
            index: newIndex,
            showAddNewPriceItemPopup: false});
    }
    
    deletePriceListItem = () => {
        const newPriceList = this.state.priceList.filter(item => item.index !== this.state.selectedPriceItem.index);
        
        this.setState({
            priceList: newPriceList,
            showAddNewPriceItemPopup: false});
    }
    
    render() {
        
        let priceList = <h6 className="text-center">The clinic does not have a price list.</h6>;
        if(this.state.priceList.length > 0) {
            priceList = this.state.priceList.map(priceItem => (
                <div key={priceItem.index} className={classes.pricelist} onClick={() => this.setState({
                    showAddNewPriceItemPopup: true,
                    selectedPriceItem: {
                        id: priceItem.id,
                        name: priceItem.name,
                        price: priceItem.price,
                        description: priceItem.description,
                        index: priceItem.index
                    },
                    selectedUpdate: true
                })}>
                    <div className={classes.pricelistHeading}>
                        <h6>{priceItem.name}</h6>
                        <h6>{priceItem.price} rsd</h6>
                    </div>
                    <p>{priceItem.description}</p>
                </div>
            ));
        }
        
        let addNewPriceItemPopup = null;
        if(this.state.showAddNewPriceItemPopup) {
            addNewPriceItemPopup =
                <>
                    <div className={classes.backgroundOverlay} onClick={() => this.setState({ showAddNewPriceItemPopup: false })}></div>
                    <div className={classes.addNewPriceItem}>
                        <FontAwesomeIcon icon={faTimes} size="1x" className={classes.cancelBtn} onClick={() => this.setState({showAddNewPriceItemPopup: false })}/>
                        <FontAwesomeIcon icon={faTrashAlt} size="1x" className={classes.deleteBtn} onClick={this.deletePriceListItem}/>
                        <h6 className="mb-4">Add pricelist item</h6>
                        <div>
                            <span>Name:</span>
                            <input type="text" value={this.state.selectedPriceItem.name} onChange={event => this.itemNameInputHandler(event)}
                                   className={this.state.errorValidation.itemName ? classes.errorBorder : ""}/>
                        </div>
                        <div>
                            <span>Price:</span>
                            <input type="number" value={this.state.selectedPriceItem.price} onChange={event => this.itemPriceInputHandler(event)}
                                   className={this.state.errorValidation.itemPrice ? classes.errorBorder : ""}/>
                        </div>
                        <div>
                            <span>Description:</span>
                            <textarea value={this.state.selectedPriceItem.description} onChange={event => this.itemDescriptionInputHandler(event)}
                                      className={this.state.errorValidation.itemDescription ? classes.errorBorder : ""}/>
                        </div>
                        <button className={["btn", "btn-md", "mt-3", classes.btnSave].join(" ")} onClick={this.savePriceListItem}>Save pricelist item</button>
                    </div>
                </>
        }
        
        return (
            <>
                <h1 className="my-5 text-center">{this.state.creating ? "Create": "Update"} clinic</h1>
                <form>
                    <div className={[classes.inputItem, this.state.errorValidation.clinicName ? classes.errorBorder : ""].join(" ")}>
                        <FontAwesomeIcon icon={faHospitalSymbol} size="1x" className={classes.inputItemIcon}/>
                        <input type="text" placeholder="Name" value={this.state.name}
                               onChange={event => this.clinicNameInputHandler(event)}/>
                    </div>
                    <div className={[classes.inputItem, this.state.errorValidation.clinicAddress ? classes.errorBorder : ""].join(" ")}>
                        <FontAwesomeIcon icon={faClinicMedical} size="1x" className={classes.inputItemIcon}/>
                        <input type="text" placeholder="Address" value={this.state.address}
                               onChange={event => this.clinicAddressInputHandler(event)}/>
                    </div>
                    <div className={[classes.inputItem, this.state.errorValidation.clinicDescription ? classes.errorBorder : ""].join(" ")}>
                        <FontAwesomeIcon icon={faFileAlt} size="1x" className={classes.inputItemIcon}/>
                        <textarea placeholder="Description" value={this.state.description}
                              onChange={event => this.clinicDescriptionInputHandler(event)}/>
                    </div>
                </form>
                <div className={classes.btns}>
                    {authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" && <Link className={classes.showAdminsBtn} to={`/clinics/${this.state.id}/administrators`}>Administrators</Link>}
                    <Link className={classes.showAdminsBtn} to={`/clinics/${this.state.id}/doctors`}>Doctors</Link>
                    <Link className={classes.showAdminsBtn} to={`/clinics/${this.state.id}/nurses`}>Nurses</Link>
                </div>
                <h2 className="mt-5 mb-4 text-center">Pricelist</h2>
                {priceList}
                <div className="d-flex mx-auto" style={{width: "70%"}}>
                    <button className={classes.addPriceItemBtn} onClick={() => this.setState({
                        showAddNewPriceItemPopup: true,
                        selectedPriceItem: {
                            id: null,
                            name: "",
                            price: 0,
                            description: "",
                            index: this.state.index
                        },
                        selectedUpdate: false
                    })}>
                        <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginRight: "5px"}} />
                        <span>Add price item</span>
                    </button>
                </div>
                <div className="d-flex justify-content-center">
                    <button className={classes.saveClinicBtn} onClick={this.saveClinicHandler}>
                        <span>Save clinic</span>
                    </button>
                </div>
                {addNewPriceItemPopup}
            </>
        )
    }
}

export default withRouter(CreateUpdateClinic);