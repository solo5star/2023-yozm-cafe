import { styled } from 'styled-components';
import { OpeningHour, OpeningHourDay } from '../types';

const DAY_MAPPER: Record<OpeningHourDay, string> = {
  MONDAY: '월',
  TUESDAY: '화',
  WEDNESDAY: '수',
  THURSDAY: '목',
  FRIDAY: '금',
  SATURDAY: '토',
  SUNDAY: '일',
};

type OpeningHoursDetailProps = {
  openingHours: OpeningHour[];
};

const OpeningHoursDetail = ({ openingHours }: OpeningHoursDetailProps) => {
  const getCurrentTime = (): string => {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');

    return `${hours}:${minutes}`;
  };

  const today = new Date().toLocaleDateString('en-US', { weekday: 'long' }).toUpperCase();
  const todayOpeningHour = openingHours.find((openingHour) => openingHour.day === today) ?? null;

  const isOpenedToday = (): boolean => {
    const currentTime = getCurrentTime();

    if (!todayOpeningHour || !todayOpeningHour.opened) return false;

    return (
      today === todayOpeningHour.day && todayOpeningHour.open <= currentTime && currentTime <= todayOpeningHour.close
    );
  };

  return (
    <Container>
      <h3>{isOpenedToday() ? '영업중' : '영업 종료'}</h3>
      <Summary>
        {todayOpeningHour?.opened
          ? `${DAY_MAPPER[todayOpeningHour.day]} ${todayOpeningHour.open} - ${todayOpeningHour.close}`
          : '휴무'}
      </Summary>

      <Details>
        {openingHours.map((openingHour) => (
          <li key={openingHour.day}>
            {DAY_MAPPER[openingHour.day]} {openingHour.opened ? `${openingHour.open} - ${openingHour.close}` : '휴무'}
          </li>
        ))}
      </Details>
    </Container>
  );
};

export default OpeningHoursDetail;

const Container = styled.div`
  display: grid;
  gap: ${({ theme }) => theme.space[2]};
  align-items: center;
`;

const Summary = styled.h3``;

const Details = styled.ul`
  display: flex;
  grid-column: 2;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[1]};

  color: ${({ theme }) => theme.color.gray};
`;