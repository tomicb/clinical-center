import React, {Component} from 'react'
import Patient from '../Examinations/ExaminationUser/ExaminationUser'
import {faCalendarAlt, faClock, faUserMd, faMoneyBillWave, faPrescriptionBottle, faTimes, faPen, faPlus, faTrashAlt} from "@fortawesome/free-solid-svg-icons";
import { Col, Row } from "react-bootstrap";
import classes from './MedicalRecord.module.css';
import axios from "axios";
import { withRouter } from "react-router";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import moment from "moment";
import authService from "../../services/auth-service";

class MedicalRecord extends Component {
    state = {
        medicalRecord: null,
        selectedMedicalRecordItem: {
            id: null,
            name: "",
            value: "",
            medicalRecord: null
        },
        updateDetails: true,
        loaded: false,
        popup: {
            showAddDetailsPopup: false,
            top: 0,
            left: 0
        }
    };
    
    componentDidMount = () => {
        if(this.props.match.params.jmbg) {
            axios
                .get(`/medical-records/${this.props.match.params.jmbg}`)
                .then((response) => {
                    this.setState({ medicalRecord: response.data, loaded: true });
                })
                .catch((error) => console.log(error));
        } else {
            axios
                .get("/medical-records")
                .then((response) => {
                    this.setState({ medicalRecord: response.data, loaded: true });
                })
                .catch((error) => console.log(error));
        }
    };
    
    saveMedicalRecordItem = () => {
        if(this.state.updateDetails) {
            axios
                .put("/medical-record-items/", this.state.selectedMedicalRecordItem)
                .then((response) => {
                    let items = this.state.medicalRecord.items.map(item => item.id === this.state.selectedMedicalRecordItem.id ? {...this.state.selectedMedicalRecordItem} : item )
                    this.setState({
                        medicalRecord: { ...this.state.medicalRecord, items: items},
                        popup: {showAddDetailsPopup: false}});
                })
                .catch((error) => console.log(error));
        } else {
            axios
                .post("/medical-record-items/", this.state.selectedMedicalRecordItem)
                .then((response) => {
                    let items = [...this.state.medicalRecord.items];
                    items.push(response.data);
                    this.setState({
                        medicalRecord: { ...this.state.medicalRecord, items: items},
                        popup: {showAddDetailsPopup: false}});
                })
                .catch((error) => console.log(error));
        }
    }
    
    deleteMedicalRecordItem = (id) => {
        axios
            .delete(`/medical-record-items/${id}`)
            .then((response) => {
                let items = this.state.medicalRecord.items.filter(item => item.id !== id)
                this.setState({medicalRecord: { ...this.state.medicalRecord, items: items}});
            })
            .catch((error) => console.log(error));
    }
    
    render() {
        if(this.state.loaded) {
            
            console.log(this.state.medicalRecord);
            console.log(this.state.hasPermision);
            
            const examinations = this.state.medicalRecord.examinations.map((examination, index) => {
                let recipes = <Col lg={1} md={2} sm={12} xs={12} />
                if(examination.recipe !== null && examination.recipe !== undefined) {
                    recipes =
                        <Col lg={1} md={2} sm={12} xs={12}>
                            <FontAwesomeIcon icon={faPrescriptionBottle} size="1x"/>
                            <span className="ml-1">{examination.recipe.medication.length}</span>
                        </Col>
                }
                
                return (
                    <Row key={index} className={["mx-0", classes.examination].join(" ")} onClick={() => this.props.history.push(`/examinations/${examination.id}`)}>
                        <Col lg={2} md={6} sm={12} xs={12}>
                            <FontAwesomeIcon icon={faClock} size="1x"/>
                            <span className="ml-1">{moment(examination.start).format('HH:mm')} - {moment(examination.end).format('HH:mm')}</span>
                        </Col>
                        <Col lg={3} md={6} sm={12} xs={12}>
                            <FontAwesomeIcon icon={faCalendarAlt} size="1x"/>
                            <span className="ml-1">{moment(examination.start).format('DD. MM. YYYY.')}</span>
                        </Col>
                        <Col lg={4} md={6} sm={12} xs={12}>
                            <FontAwesomeIcon icon={faUserMd} size="1x"/>
                            <span className="ml-1">Dr. {examination.doctor.firstName + " " + examination.doctor.lastName}</span>
                        </Col>
                        {recipes}
                        <Col lg={2} md={4} sm={12} xs={12}>
                            <FontAwesomeIcon icon={faMoneyBillWave} size="1x"/>
                            <span className="ml-1">{examination.price} RSD</span>
                        </Col>
                        <Col xl={12} className="mt-2">
                            <h5>{examination.title}</h5>
                            <p className="m-0">{examination.report}</p>
                        </Col>
                    </Row>
                )
            })
            
            let medicalRecordItems = <h6>There are not any medical record details.</h6>
            if(this.state.medicalRecord.items.length > 0)
                medicalRecordItems = this.state.medicalRecord.items.map(item => (
                    <div className={["mr-2", classes.medicalRecordItem].join(" ")} key={item.id}>
                        <span>{item.name + ": " + item.value}</span>
                        {authService.getCurrentRole() === "ROLE_DOCTOR" &&
                        <>
                            <FontAwesomeIcon icon={faPen} className={classes.medicalRecordItemIcon} size="1x"
                                             onClick={(event) => {
                                                 return this.setState(
                                                     {
                                                         selectedMedicalRecordItem: {
                                                             id: item.id,
                                                             name: item.name,
                                                             value: item.value,
                                                             medicalRecord: this.state.medicalRecord.id
                                                         },
                                                         popup: {
                                                             showAddDetailsPopup: true,
                                                             top: event.pageY,
                                                             left: event.pageX
                                                         },
                                                         updateDetails: true
                                                     })
                                             }}/>
                            <FontAwesomeIcon icon={faTrashAlt} className={classes.medicalRecordItemIcon} size="1x" onClick={() => this.deleteMedicalRecordItem(item.id)}/>
                        </>}
                    </div>
                ));
            
            let addDetailsPopup = null;
            if(this.state.popup.showAddDetailsPopup) {
                addDetailsPopup =
                    <>
                        <div className={classes.backgroundOverlay} onClick={(event) => this.setState({ popup: {showAddDetailsPopup: false }})}></div>
                        <div className={classes.addMedicalRecordItem} style={{top: this.state.popup.top, left:this.state.popup.left}}>
                            <FontAwesomeIcon icon={faTimes} size="1x" className={classes.cancelBtn} onClick={() => this.setState({ popup: {showAddDetailsPopup: false }})}/>
                            <h6 className="mb-4">Add patient details</h6>
                            <div>
                                <span>Name:</span>
                                <input type="text" value={this.state.selectedMedicalRecordItem.name} onChange={event => this.setState({ selectedMedicalRecordItem: { ...this.state.selectedMedicalRecordItem, name: event.target.value }})}/>
                            </div>
                            <div>
                                <span>Value:</span>
                                <input type="text" value={this.state.selectedMedicalRecordItem.value} onChange={event => this.setState({ selectedMedicalRecordItem: { ...this.state.selectedMedicalRecordItem, value: event.target.value }})}/>
                            </div>
                            <button className={["btn", "btn-md", "mt-3", classes.btnSave].join(" ")} onClick={this.saveMedicalRecordItem}>Save details</button>
                        </div>
                    </>
            }
            
            let data = <h3 className="text-center mt-5">You have no permision to view this medical record.</h3>
            if(this.state.medicalRecord.permision) {
                data =
                    <>
                        <div className={classes.medicalRecordItems}>
                            <h4>Details</h4>
                            {medicalRecordItems}
                            {authService.getCurrentRole() === "ROLE_DOCTOR" && <button className={classes.medicalRecordItemAddBtn} onClick={event => {
                                return this.setState(
                                    {
                                        selectedMedicalRecordItem: {
                                            id: null,
                                            name: "",
                                            value: "",
                                            medicalRecord: this.state.medicalRecord.id
                                        },
                                        popup: {
                                            showAddDetailsPopup: true,
                                            top: event.pageY,
                                            left: event.pageX
                                        },
                                        updateDetails: false
                                    })
                            }}>
                                <FontAwesomeIcon icon={faPlus} size="1x"/>
                            </button>}
                        </div>
            
                        <div className={classes.examinations}>
                            <h4 className="ml-3">Examinations</h4>
                            {examinations}
                        </div>
                    </>
            }
            
            return (
                <>
                    <h1 className="my-5 text-center">{this.state.medicalRecord.patient.firstName}'s medial record</h1>
                    <Patient user={this.state.medicalRecord.patient} medicalRecord/>
                    {addDetailsPopup}
                    {data}
                </>
            );
        };
        return <></>;
    }
}

export default withRouter(MedicalRecord);