-- Adresy
INSERT INTO ADDRESS (id, address_line1, address_line2, city, postal_code) VALUES
                                                                              (1, 'Wroclawska', 'Mieszkanie 10','Warszawa','00-005'),
                                                                              (2, 'Ulica Glowna 1', NULL, 'Warszawa', '00-001'),
                                                                              (3, 'Ulica Pieciomorgowa 5', NULL, 'Krakow', '30-002'),
                                                                              (4, 'Aleje Jerozolimskie 100', 'Mieszkanie 12', 'Poznan', '60-300'),
                                                                              (5, 'Zielona 25', 'Mieszkanie 3', 'Gdansk', '80-400'),
                                                                              (6, 'Rynkowska 8', 'Mieszkanie 1', 'Wroclaw', '50-500'),
                                                                              (7, 'Nowa 15', 'Mieszkanie 4', 'Lodz', '90-100');

-- Lekarze
INSERT INTO DOCTOR (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) VALUES
                                                                                                                       (1, 'Jan', 'Kowalski', '123456789', 'jan.kowalski@przyklad.com', 'D001', 'SURGEON', 1),
                                                                                                                       (2, 'Anna', 'Nowak', '234567890', 'anna.nowak@przyklad.com', 'D002', 'GP', 2),
                                                                                                                       (3, 'Piotr', 'Zielinski', '345678901', 'piotr.zielinski@przyklad.com', 'D003', 'DERMATOLOGIST', 3),
                                                                                                                       (4, 'Maria', 'Wisniewska', '456789012', 'maria.wisniewska@przyklad.com', 'D004', 'GP', 4),
                                                                                                                       (5, 'Krzysztof', 'Lewandowski', '567890123', 'krzysztof.lewandowski@przyklad.com', 'D005', 'OCULIST', 5);

-- Pacjenci
INSERT INTO PATIENT (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id, age) VALUES
                                                                                                                        (1, 'Katarzyna', 'Mazur', '987654321', 'katarzyna.mazur@przyklad.com', 'P001', '1985-05-15', 1,38),
                                                                                                                        (2, 'Marek', 'Kowalczyk', '876543210', 'marek.kowalczyk@przyklad.com', 'P002', '1992-10-25', 2,31),
                                                                                                                        (3, 'Ewa', 'Bak', '765432109', 'ewa.bak@przyklad.com', 'P003', '1990-03-10', 3,33),
                                                                                                                        (4, 'Tomasz', 'Szymanski', '654321098', 'tomasz.szymanski@przyklad.com', 'P004', '1982-07-30', 4,41),
                                                                                                                        (5, 'Agnieszka', 'Nowicka', '543210987', 'agnieszka.nowicka@przyklad.com', 'P005', '2000-02-20', 5,25);

-- Leczenia
INSERT INTO MEDICAL_TREATMENT (id, description, type) VALUES
                                                          (1, 'USG brzucha', 'USG'),
                                                          (2, 'RTG klatki piersiowej', 'RTG'),
                                                          (3, 'Badanie krwi', 'Laboratoryjne'),
                                                          (4, 'Ekg serca', 'Diagnostyczne'),
                                                          (5, 'Rezonans magnetyczny', 'MRI');

-- Wizyty
INSERT INTO VISIT (id, description, time, doctor_id, patient_id, treatment_id) VALUES
                                                                                   (1, 'Kontrola', '2024-12-01 10:00:00', 1, 1, 1),
                                                                                   (2, 'Wizyta kardiologiczna', '2024-12-02 11:00:00', 2, 2, 2),
                                                                                   (3, 'Kontrola pediatryczna', '2024-12-03 12:00:00', 3, 3, 3),
                                                                                   (4, 'Konsultacja dermatologiczna', '2024-12-04 13:00:00', 4, 4, 4),
                                                                                   (5, 'Wizyta stomatologiczna', '2024-12-05 14:00:00', 5, 5, 5),
                                                                                   (6, 'Wizyta kontrolna u chirurga', '2024-12-06 15:00:00', 1, 1, 2),
                                                                                   (7, 'Konsultacja kardiologiczna', '2024-12-07 09:30:00', 2, 1, 3),
                                                                                   (8, 'Wizyta pediatryczna', '2024-12-08 10:00:00', 3, 3, 4),
                                                                                   (9, 'Rehabilitacja', '2024-12-09 11:00:00', 2, 4, 5),
                                                                                   (10, 'Przeglad stomatologiczny', '2024-12-10 12:00:00', 5, 5, 1),
                                                                                   (11, 'Kontrola dermatologiczna', '2024-12-11 14:00:00', 4, 2, 2),
                                                                                   (12, 'Wizyta kontrolna pediatryczna', '2024-12-12 15:00:00', 3, 2, 3),
                                                                                   (13, 'Wizyta kardiologiczna', '2024-12-13 13:30:00', 2, 5, 4),
                                                                                   (14, 'Konsultacja stomatologiczna', '2024-12-14 16:00:00', 5, 3, 5),
                                                                                   (15, 'Wizyta kontrolna', '2024-12-15 17:00:00', 1, 4, 1);