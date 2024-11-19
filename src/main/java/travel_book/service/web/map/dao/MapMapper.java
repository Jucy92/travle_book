package travel_book.service.web.map.dao;

import org.apache.ibatis.annotations.Mapper;
import travel_book.service.web.map.dto.LocationModel;
import travel_book.service.web.map.dto.LocationSearchCond;

import javax.xml.stream.Location;
import java.util.List;
import java.util.Optional;

@Mapper         // 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스

public interface MapMapper {

    void saveLocation(LocationModel location);  // Member.save는 Member 반환인데 여기는 저장만하고 되돌려줄 필요 없겠지?
    Long saveStorageM(LocationModel location);
    void saveStorageD(LocationModel location);

    List<LocationModel> LocationFindById(Long memberId);    // M/D 테이블 만들면서 불필요해짐
    List<LocationModel> storageMFindByUserId(String userId);
    List<LocationModel> storageDFindByTravelId(Long userId);

    void copyOfAllItinerary(String userId, Long travelId);

}
