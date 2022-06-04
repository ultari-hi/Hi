package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationFiltering {
    @Column(name = "parking_lot")
    private boolean parkingLot;

    @Column(name = "guest_kitchen")
    private boolean guestKitchen;

    @Column(name = "washing_machine")
    private boolean washingMachine;

    @Column(name = "drying_machine")
    private boolean dryingMachine;

    private boolean iron;

    private boolean bbq;

    private boolean lounge;

    @Column(name = "public_pc")
    private boolean publicPc;

    private boolean printer;

    private boolean pool;

    private boolean spa;

    private boolean sauna;

    @Column(name = "convenience_store")
    private boolean convenienceStore;

    private boolean microwave;

    @Column(name = "public_shower_room")
    private boolean publicShowerRoom;

    private boolean wifi;

    @Column(name = "coffee_port")
    private boolean coffeePort;

    @Column(name = "hair_dryer")
    private boolean hairDryer;

    @Column(name = "room_shower_room")
    private boolean roomShowerRoom;

    @Column(name = "bathroom_ware")
    private boolean bathroomWare;

    @Column(name = "air_conditioner")
    private boolean airConditioner;

    private boolean refrigerator;

    private boolean television;

    @Column(name = "room_microwave")
    private boolean roomMicrowave;

    private boolean computer;

    private boolean breakfast;

    private boolean lunch;

    private boolean dinner;

    @Column(name = "smoking_in_room")
    private boolean smokingInRoom;

    @Column(name = "cooking_in_room")
    private boolean cookingInRoom;

    @Column(name = "companion_for_pets")
    private boolean companionForPets;

    @Column(name = "airport_pick_up")
    private boolean airportPickUp;

    private boolean campfire;

    @Column(name = "bike_rental")
    private boolean bikeRental;

    @Column(name = "full_time_access")
    private boolean fullTimeAccess;

    @Column(name = "no_foreigner")
    private boolean noForeigner;

    @Column(name = "no_child")
    private boolean noChild;

    @Column(name = "luggage_storage")
    private boolean luggageStorage;
}
