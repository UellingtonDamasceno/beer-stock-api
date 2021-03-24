package beer.stock.api.mapper;

import beer.stock.api.dto.BeerDTO;
import beer.stock.api.entity.Beer;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Uellington Damasceno
 */
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toModel(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);
}
