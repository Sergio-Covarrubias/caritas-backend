import os, time, uuid
import requests
from faker import Faker
from datetime import timedelta

fake = Faker()


BASE_URL = 'http://localhost:8080'
WIPE_ENDPOINT = '/api/dev/wipe'
USERS_ENDPOINT = '/api/users'
SERVICES_ENDPOINT = '/api/services'
HOSTELS_ENDPOINT = '/api/hostels'
HOSTEL_SERVICES_ENDPOINT = '/api/hostel-services'
RESERVATIONS_ENDPOINT = '/api/reservations'
SERVICE_INTERESTS_ENDPOINT = '/api/service-interests'
SERVICE_RESERVATIONS_ENDPOINT = '/api/service-reservations'

def wipe_data():
    r = requests.post(BASE_URL + WIPE_ENDPOINT, json={})
    r.raise_for_status()

def create_user():
    payload = {'id': '123456', 'firstName': 'Sergio Alejandro', 'lastName': 'Covarrubias Cázares', 'email': 'example@gmail.com', 'phoneNumber': '6444123456'}
    r = requests.post(BASE_URL + USERS_ENDPOINT, json=payload)
    r.raise_for_status()
    return r.json()

def create_services():
    services_data = [
        {'displayName': 'Laundry', 'type': 'laundry'},
        {'displayName': 'Bath', 'type': 'bath'}
    ]

    results = []
    for service in services_data:
        payload = service
        r = requests.post(BASE_URL + SERVICES_ENDPOINT, json=payload)
        r.raise_for_status()
        results.append(r.json())

    return results

def create_hostels():
    hostels_data = [
        {'name': 'Albergue A', 'description': 'Descripción del albergue A'},
        {'name': 'Albergue B', 'description': 'Descripción del albergue B'},
    ]

    results = []
    for hostel in hostels_data:
        payload = hostel
        r = requests.post(BASE_URL + HOSTELS_ENDPOINT, json=payload)
        r.raise_for_status()
        results.append(r.json())

    return results

def create_hostel_services(hostels, services):
    hostel_A = hostels[0]
    hostel_B = hostels[1]

    laundry_service = services[0]
    bath_service = services[1]

    hostel_services_data = [
        {'hostelId': hostel_A['id'], 'serviceId': laundry_service['id']},
        {'hostelId': hostel_A['id'], 'serviceId': bath_service['id']},
        {'hostelId': hostel_B['id'], 'serviceId': laundry_service['id']}
    ]

    results = []
    for hostel_service in hostel_services_data:
        payload = hostel_service
        r = requests.post(BASE_URL + HOSTEL_SERVICES_ENDPOINT, json=payload)
        r.raise_for_status()
        results.append(r.json())
    
    return results

def create_reservation(user_id, hostel_id):    
    start_date = fake.date_between(start_date='+1d', end_date='+30d')
    end_date = start_date + timedelta(days=fake.random_int(min=1, max=14))


    start = start_date.strftime("%Y-%m-%d")
    end = end_date.strftime("%Y-%m-%d")
    
    payload = {
        'userId': str(user_id),
        'hostelId': str(hostel_id),
        'startDate': start,
        'endDate': end,
        'peopleCount': fake.random_int(min=1, max=6),
    }

    r = requests.post(BASE_URL + RESERVATIONS_ENDPOINT, json=payload)
    r.raise_for_status()
    return r.json()

def create_service_interest_A(reservation, services):
    laundry_service = services[0]
    bath_service = services[1]

    service_interests_data = [
        {'reservationId': reservation['id'], 'serviceId': bath_service['id']}
    ]

    results = []
    for service_interest in service_interests_data:
        payload = service_interest
        r = requests.post(BASE_URL + SERVICE_INTERESTS_ENDPOINT, json=payload)
        r.raise_for_status()
        results.append(r.json())

    return results

def create_service_interest_B(reservation, services):
    laundry_service = services[0]
    bath_service = services[1]

    service_interests_data = [
        {'reservationId': reservation['id'], 'serviceId': laundry_service['id']}
    ]

    results = []
    for service_interest in service_interests_data:
        payload = service_interest
        r = requests.post(BASE_URL + SERVICE_INTERESTS_ENDPOINT, json=payload)
        r.raise_for_status()
        results.append(r.json())

    return results

def create_service_reservation_A(reservation, services):
    laundry_service = services[0]
    bath_service = services[1]

    service_reservations_data = [
        {'reservationId': reservation['id'], 'serviceId': bath_service['id'], 'externalReservationId': 'abc'}
    ]

    results = []
    for service_reservation in service_reservations_data:
        payload = service_reservation
        r = requests.post(BASE_URL + SERVICE_RESERVATIONS_ENDPOINT, json=payload)
        r.raise_for_status()
        results.append(r.json())

    return results

def create_service_reservation_B(reservation, services):
    laundry_service = services[0]
    bath_service = services[1]

    service_reservations_data = [
        {'reservationId': reservation['id'], 'serviceId': laundry_service['id'], 'externalReservationId': 'xyz'}
    ]

    results = []
    for service_reservation in service_reservations_data:
        payload = service_reservation
        r = requests.post(BASE_URL + SERVICE_RESERVATIONS_ENDPOINT, json=payload)
        r.raise_for_status()
        results.append(r.json())

    return results

def main():
    try:
        print('WIPING DATA')
        wipe_data()

        user = create_user()
        print('-- USER --')
        print(user)

        services = create_services()
        print('-- SERVICES --')
        print(services)

        hostels = create_hostels()
        print('-- HOSTELS --')
        print(hostels)
        
        hostel_services = create_hostel_services(hostels, services)
        print('-- HOSTEL SERVICES --')
        print(hostel_services)

        reservation_A = create_reservation(user['id'], hostels[0]['id'])
        reservation_B = create_reservation(user['id'], hostels[1]['id'])
        print('-- RESERVATIONS --')
        print([reservation_A, reservation_B])

        service_interests_A = create_service_interest_A(reservation_A, services)
        service_interests_B = create_service_interest_B(reservation_B, services)
        print('-- SERVICE INTERESTS --')
        print(service_interests_A + service_interests_B)


        service_reservations_A = create_service_reservation_A(reservation_A, services)
        service_reservations_B = create_service_reservation_B(reservation_B, services)
        print('-- SERVICE RESERVATIONS --')
        print(service_reservations_A + service_reservations_B)

    except Exception as e:
        print(e)

if __name__ == '__main__':
    main()
