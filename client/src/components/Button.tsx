import { ButtonHTMLAttributes } from 'react';
import styled, { css } from 'styled-components';

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: 'default' | 'outlined' | 'disabled';
  fullWidth?: boolean;
};

const Button = ({ children, variant = 'default', fullWidth = false, ...rest }: ButtonProps) => {
  return (
    <Container variant={variant} fullWidth={fullWidth} {...rest}>
      {children}
    </Container>
  );
};

export default Button;

const ButtonVariants = {
  disabled: css`
    color: ${(props) => props.theme.color.white};
    background-color: ${(props) => props.theme.color.gray};
    border: none;
  `,
  outlined: css`
    color: ${(props) => props.theme.color.gray};
    background-color: ${(props) => props.theme.color.white};
    border: 2px solid ${(props) => props.theme.color.primary};
  `,
  default: css`
    color: ${(props) => props.theme.color.white};
    background-color: ${(props) => props.theme.color.primary};
    border: none;
  `,
};

const Container = styled.button<ButtonProps>`
  cursor: pointer;

  padding: ${({ theme }) => theme.space['1.5']} 0;

  font-size: 16px;
  font-weight: 500;

  border-radius: 40px;
  ${(props) => ButtonVariants[props.variant || 'default']}
  ${(props) => props.fullWidth && 'width: 100%;'}
`;