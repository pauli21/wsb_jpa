insert into address (id, address_line1, address_line2, city, postal_code)
            values (1, 'xx', 'yy', 'city', '62-030');

-- Adresy
INSERT INTO ADDRESS (id, address_line1, address_line2, city, postal_code) VALUES
(2, 'Ulica Główna 1', NULL, 'Warszawa', '00-001'),
(3, 'Ulica Pięciomorgowa 5', NULL, 'Kraków', '30-002'),
(4, 'Aleje Jerozolimskie 100', 'Mieszkanie 12', 'Poznań', '60-300'),
(5, 'Zielona 25', 'Mieszkanie 3', 'Gdańsk', '80-400'),
(6, 'Rynkowska 8', 'Mieszkanie 1', 'Wrocław', '50-500'),
(7, 'Nowa 15', 'Mieszkanie 4', 'Łódź', '90-100');

-- Lekarze
INSERT INTO DOCTOR (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) VALUES
(1, 'Jan', 'Kowalski', '123456789', 'jan.kowalski@przyklad.com', 'D001', 'Chirurg', 1),
(2, 'Anna', 'Nowak', '234567890', 'anna.nowak@przyklad.com', 'D002', 'Kardiolog', 2),
(3, 'Piotr', 'Zieliński', '345678901', 'piotr.zielinski@przyklad.com', 'D003', 'Pediatra', 3),
(4, 'Maria', 'Wiśniewska', '456789012', 'maria.wisniewska@przyklad.com', 'D004', 'Dermatolog', 4),
(5, 'Krzysztof', 'Lewandowski', '567890123', 'krzysztof.lewandowski@przyklad.com', 'D005', 'Stomatolog', 5);

-- Pacjenci
INSERT INTO PATIENT (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id) VALUES
(1, 'Katarzyna', 'Mazur', '987654321', 'katarzyna.mazur@przyklad.com', 'P001', '1985-05-15', 1),
(2, 'Marek', 'Kowalczyk', '876543210', 'marek.kowalczyk@przyklad.com', 'P002', '1992-10-25', 2),
(3, 'Ewa', 'Bąk', '765432109', 'ewa.bak@przyklad.com', 'P003', '1990-03-10', 3),
(4, 'Tomasz', 'Szymański', '654321098', 'tomasz.szymanski@przyklad.com', 'P004', '1982-07-30', 4),
(5, 'Agnieszka', 'Nowicka', '543210987', 'agnieszka.nowicka@przyklad.com', 'P005', '2000-02-20', 5);

-- Leczenia
INSERT INTO MEDICAL_TREATMENT (id, description, type) VALUES
(1, 'USG brzucha', 'USG'),
(2, 'RTG klatki piersiowej', 'RTG'),
(3, 'Badanie krwi', 'Laboratoryjne'),
(4, 'Ekg serca', 'Diagnostyczne'),
(5, 'Rezonans magnetyczny', 'MRI');

-- Wizyty
INSERT INTO VISIT (id, description, time, doctor_id, patient_id, treatment_id) VALUES
(1, 'Kontrola zdrowia', '2024-12-01 10:00:00', 1, 1, 1),
(2, 'Wizyta kardiologiczna', '2024-12-02 11:00:00', 2, 2, 2),
(3, 'Kontrola pediatryczna', '2024-12-03 12:00:00', 3, 3, 3),
(4, 'Konsultacja dermatologiczna', '2024-12-04 13:00:00', 4, 4, 4),
(5, 'Wizyta stomatologiczna', '2024-12-05 14:00:00', 5, 5, 5),
(6, 'Wizyta kontrolna u chirurga', '2024-12-06 15:00:00', 1, 1, 2), -- Katarzyna Mazur - chirurg
(7, 'Konsultacja kardiologiczna', '2024-12-07 09:30:00', 2, 1, 3),     -- Katarzyna Mazur - kardiolog
(8, 'Wizyta pediatryczna', '2024-12-08 10:00:00', 3, 3, 4),             -- Ewa Bąk - pediatra
(9, 'Rehabilitacja', '2024-12-09 11:00:00', 2, 4, 5),                   -- Tomasz Szymański - kardiolog
(10, 'Przegląd stomatologiczny', '2024-12-10 12:00:00', 5, 5, 1),       -- Agnieszka Nowicka - stomatolog
(11, 'Kontrola dermatologiczna', '2024-12-11 14:00:00', 4, 2, 2),       -- Marek Kowalczyk - dermatolog
(12, 'Wizyta kontrolna pediatryczna', '2024-12-12 15:00:00', 3, 2, 3),   -- Marek Kowalczyk - pediatra
(13, 'Wizyta kardiologiczna', '2024-12-13 13:30:00', 2, 5, 4),          -- Agnieszka Nowicka - kardiolog
(14, 'Konsultacja stomatologiczna', '2024-12-14 16:00:00', 5, 3, 5),    -- Ewa Bąk - stomatolog
(15, 'Wizyta kontrolna', '2024-12-15 17:00:00', 1, 4, 1);               -- Tomasz Szymański - chirurg
