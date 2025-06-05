package milla.rent_receipt_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import milla.rent_receipt_service.Entities.RentEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentRequestDTO {
    private RentEntity rent;
    private List<String> subClients;

}
